const {
  func,
  string,
  bool,
  arrayOf,
  object,
  element,
} = React.PropTypes;

/**
 * Stateful (smart) components
 */

/* stomp view begin */
class StompView extends  React.Component {
  constructor(props) {
    super(props);
    this.state = {
      connected: false,
    };
    this.stompEndpoint = "/stomp-chat-endpoint";
    this.messagesTopic = "/topic/chat-messages";
    this.sendMessageDestination = "/app/api/v1/pub-sub/send-message";
    this.start = this.start.bind(this);
    this.finish = this.finish.bind(this);
    this.subscribe = this.subscribe.bind(this);
    this.sendStompMessage = this.sendStompMessage.bind(this);
  }
  subscribe() {
    this.subscription = this.client.subscribe(
      this.messagesTopic,
      this.props.addMessage
    );
    this.setState({ connected: true });
  }
  start() {
    const socket = new SockJS(this.stompEndpoint);
    this.client = Stomp.over(socket);
    this.client.heartbeat.outgoing = 120000;
    this.client.heartbeat.incoming = 5000;
    this.client.connect({}, this.subscribe);
    this.state.connected = true;
  }
  finish () {
    if (this.subscription) this.subscription.unsubscribe();
    if (this.client) this.client.disconnect();
    this.setState({ connected: false });
  }
  messageWith(body) {
    return JSON.stringify({
      owner: this.props.owner,
      body,
    });
  }
  sendStompMessage({ keyCode, target}) {
    const { value } = target;
    if (13 !== keyCode || !value || !value.trim().length) return;
    this.client.send(
      this.sendMessageDestination,
      this.state.headers,
      this.messageWith(value)
    );
    target.value = "";
  }
  componentDidMount() {
    this.start();
  }
  componentWillUnmount() {
    this.finish();
  }
  render() {
    return (
      this.state.connected
        ? <div>
            <input type="text"
                   ref={input => this.ref = input}
                   onKeyDown={this.sendStompMessage}
                   placeholder="send message to stomp broker"/>
            <button className="btn"
                    onClick={this.finish}>finish</button>
          </div>
        : <div>
            <button className="btn"
                    onClick={this.start}>start</button>
          </div>
    );
  }
}

StompView.propTypes = {
  owner: string.isRequired,
  addMessage: func.isRequired,
};
/* stomp view end */

/* main chat application component begin */
class Chat extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      owner: "",
      edit: props.edit,
      messages: props.messages,
    };
    this.setUsername = this.setUsername.bind(this);
    this.addMessage = this.addMessage.bind(this);
  }
  addMessage({ body }) {
    const message = body;
    this.setState({
      messages: [
        JSON.parse(message),
        ...this.state.messages,
      ],
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
                <StompView owner={owner}
                           addMessage={this.addMessage}/>
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
/* main chat application component end */

/**
 * Stateless functional (dump) components
 */

/* owner view begin */
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
/* owner view end */

/* header view begin */
const Header = ({ owner }) => <div>
  <div className="card-title">
    <h5 className="blue-grey-text">hello {owner}!</h5>
  </div>
</div>;

Header.propTypes = {
  owner: string.isRequired,
};
/* header view end */

/* messages list view begin */
const Messages = ({ messages }) => <ul>
  {
    ! messages
      ? <li>Loading...</li>
      : messages.map((message, id) => <li key={id}>{message.owner} {message.body}</li>)
  }
</ul>;

Messages.propTypes = {
  messages: arrayOf(object).isRequired,
};
/* messages list view end */

/* navbar begin */
const Nav = ({ appName }) => <nav className="light-blue lighten-3">
  <div className="navbar-wrapper container">
    <div className="brand-logo center">{appName}</div>
  </div>
</nav>;

Nav.propTypes = {
  appName: string,
};

Nav.defaultProps = {
  appName: "STOMP Chat",
};
/* navbar view end */

/**
 * helpers
 */

/* returns value that is greater than zero */
const positive = value => !!value && value > 0 ? value : 1;

/*
 parse query param from url
 example: `getParam("q")`
 returns 3 for http://localhost:3000/?q=3&y=2
 */
const getParam = (param = "q", value = 1) => {
  // const url = window.location.href,
  const uri = window.location.search,
    name = param.replace(/[\[\]]/g, "\\$&"),
    regex = new RegExp(`[?&]${name}(=([^&#]*)|&|#|$)`),
    results = regex.exec(uri); // = regex.exec(url);
  if (!results || !results.length || !results[2]) return positive(value);
  const resultStr = decodeURIComponent(results[2].replace(/\+/g, " "));
  return positive(+resultStr);
};

/*
 generate array of elements with size
 example: `generateArray(<div>hi!</div>)
 */
const generateArray = (element = <Chat/>, size = getParam()) =>
  [...new Array(size)].map(_ => element);

/**
 * bootstrapping
 */

/* Apps (parent container component) begin */
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
/** Apps (parent container component) end */

Apps.defaultProps = {
  children: generateArray(),
};

/* render DOM */
ReactDOM.render(
  <Apps/>,
  document.getElementById("app")
);
