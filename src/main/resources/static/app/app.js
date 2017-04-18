const {
  func,
  string,
  bool,
  arrayOf,
  object,
  element,
} = React.PropTypes;

/** owner view */
const Name = ({ setUsername }) => {
  let inputRef;
  return <input type="text"
                onKeyDown={setUsername}
                onChange={input => inputRef = input}
                placeholder="please, enter you name"/>;
};

Name.propTypes = {
  setUsername: func.isRequired,
};

/** header view */
const Header = ({ owner }) => <div>
  <div className="card-title">
    <h5 className="blue-grey-text">hello {owner}!</h5>
  </div>
</div>;

Header.propTypes = {
  owner: string.isRequired,
};

/** rest client helper */
const onInput = (owner = "anonymous", { keyCode, target }) => {
  const { value } = target;
  if (keyCode !== 13 || value.trim().length === 0) return;
  fetch("/api/v1/command/send-message", {
    method: "post",
    headers: { "content-type": "application/json; charset=UTF-8" },
    body: JSON.stringify({
      owner,
      body: value,
    }),
  });
  target.value = "";
};

/** message sender view */
const MessageSender = ({ owner }) => {
  let inputRef;
  return <input type="text"
                placeholder="enter message"
                ref={input => inputRef = input}
                onKeyDown={e => onInput(owner, e)}/>;
};

MessageSender.propTypes = {
  owner: string.isRequired,
};

/** messages list view */
const Messages = ({ messages }) => <ul>
  {
    ! messages
      ? <li>Loading...</li>
      : messages.map((message, id) => <li key={id}>{message.owner}: {message.body}</li>)
  }
</ul>;

Messages.propTypes = {
  messages: arrayOf(object).isRequired,
};

/** main chat application (smart) component */
class Chat extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      edit: props.edit,
      owner: "",
      messages: props.messages,
    };
    this.toggleEdit = this.toggleEdit.bind(this);
    this.setUsername = this.setUsername.bind(this);
  }
  componentDidMount() {
    this.source = new EventSource("/api/v1/query/subscribe/chat-messages");
    this.source.addEventListener("chat-message-event", ({ data }) => {
      const { owner, body } = JSON.parse(data);
      this.setState({
        messages: [
          { owner, body },
          ...this.state.messages,
        ],
      });
    });
  }
  toggleEdit() {
    this.setState({
      edit: !this.state.edit,
    });
  }
  setUsername({ keyCode, target }) {
    const { value } = target;
    if (keyCode !== 13 || value.trim().length === 0) return;
    this.setState({
      edit: false,
      owner: value,
    });
    target.value = "";
  }
  render() {
    const { edit, owner, messages } = this.state;
    return (
      <div className="container">
        {
          edit
            ? <Name setUsername={this.setUsername}/>
            : <div>
                <Header owner={owner}/>
                <MessageSender owner={owner}/>
                <Messages messages={messages}/>
              </div>
        }
      </div>
    );
  }
}

Chat.propTypes = {
  edit: bool.isRequired,
  messages: arrayOf(object).isRequired,
};

Chat.defaultProps = {
  edit: true,
  messages: [
    {
      owner: "system",
      body: "We salute you!"
    }
  ],
};

/** Nav (reusable navbar) */
const Nav = ({ appName }) => <nav className="light-blue lighten-3">
  <div className="navbar-wrapper container">
    <div className="brand-logo center">{appName}</div>
  </div>
</nav>;

Nav.propTypes = {
  appName: string,
};

Nav.defaultProps = {
  appName: "SSE Chat",
};

/** Apps (parent container component) */
const Apps = props => <div>
  <Nav/>
  <div>
    {
      !props || !props.children
        ? <div>Loading... (required at least one child)</div>
        : !props.children.length
            ? <props.children.type single={true}
                                   {...props.children.props}
                                   {...props}>{props.children}</props.children.type>
            : props.children.map((child, key) =>
                React.cloneElement(child, {...props, key, single: false}))
    }
  </div>
</div>;

Apps.propTypes = {
  // // require only single child:
  // children: element.isRequired,
  // required 2 or more Components as a children:
  children: arrayOf(object).isRequired,
};

/** returns value that is greater than zero */
const positive = value => !!value && value > 0 ? value : 1;

/**
 * parse query param from url
 * example: `getParam('q')`
 * returns 3 for http://localhost:3000/?q=3&y=2
 */
const getParam = (param = 'q', value = 1) => {
  // const url = window.location.href,
  const uri = window.location.search,
    name = param.replace(/[\[\]]/g, "\\$&"),
    regex = new RegExp(`[?&]${name}(=([^&#]*)|&|#|$)`),
    results = regex.exec(uri); // = regex.exec(url);
  if (!results || !results.length || !results[2]) return positive(value);
  const resultStr = decodeURIComponent(results[2].replace(/\+/g, " "));
  return positive(+resultStr);
};

/**
 *  generate array of elements with size
 *  example: `generateArray(<div>hi!</div>)
 */
const generateArray = (element = <Chat/>, size = getParam()) =>
  [...new Array(size)].map(_ => element);

Apps.defaultProps = {
  children: generateArray(),
};

/** bootstrap app */
ReactDOM.render(
  <Apps/>,
  document.getElementById("app")
);
