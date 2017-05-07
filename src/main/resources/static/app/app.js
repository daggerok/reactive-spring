const Nav = () => <nav className="light-blue lighten-3">
  <div className="navbar-wrapper parallax-container">
    <div className="brand-logo center">WebSocket Chat</div>
  </div>
</nav>;

const Name = ({ setUsername }) => {
  let inputRef;
  return <div className="input-field">
    <input type="text"
           id={Date.now()}
           onKeyDown={setUsername}
           onChange={input => inputRef = input}
           placeholder="please, enter you name"/>
  </div>;
};

const Header = ({ owner }) => <div>
  <div className="card-title">
    <h5 className="blue-grey-text">hello {owner}!</h5>
  </div>
</div>;

const Messages = ({ messages }) => <ul>
  {
    ! messages
      ? <li>Loading...</li>
      : messages.map((message, id) => <li id={message.id || id}
                                          key={message.id || id}>{message.owner}: {message.body}</li>)
  }
</ul>;

const toMessage = (owner = "anonymous", { keyCode, target }) => {
  const { value } = target;
  if (keyCode !== 13 || value.trim().length === 0) return;
  const json = JSON.stringify({ owner, body: value.trim(), });
  target.value = "";
  target.focus();
  return json;
};

const MessageSender = ({ owner, send }) => {
  let inputRef;
  return <div className="input-field">
    <input type="text"
           id={Date.now()}
           placeholder="enter message"
           ref={input => inputRef = input}
           onKeyDown={e => send(toMessage(owner, e))}/>
  </div>;
};

class Chat extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      owner: "",
      edit: props.edit,
      messages: props.messages,
    };
    this.send = this.send.bind(this);
    this.toggleEdit = this.toggleEdit.bind(this);
    this.setUsername = this.setUsername.bind(this);
  }
  send(message) {
    if (message) this.ws.send(message);
  }
  toggleEdit() {
    this.setState({ edit: !this.state.edit, });
  }
  setUsername({ keyCode, target }) {
    const { value } = target;
    if (keyCode !== 13 || value.trim().length === 0) return;
    this.setState({ edit: false, owner: value, });
    target.value = "";
  }
  componentDidMount() {
    this.ws = new WebSocket("ws://localhost:3000/api/v1/ws/subscribe/messages");
    this.ws.onmessage = ({ data }) => {
      const { id, owner, body } = JSON.parse(data);
      this.setState({
        messages: [
          { id , owner, body },
          ...this.state.messages,
        ],
      });
    };
    // this.ws.onopen = console.log;
    // this.ws.onerror = console.log;
    // this.ws.onclose = console.log;
  }
  componentWillUnmount () {
    if (this.ws && this.ws.close) this.ws.close();
  }
  render() {
    const { edit, owner, messages } = this.state;
    return (
      <div className="parallax-container" style={{ padding: '2% 1%' }}>
        <Nav/>
        {
          edit
            ? <Name setUsername={this.setUsername}/>
            : <div>
                <Header owner={owner}/>
                <MessageSender owner={owner}
                               send={this.send}/>
                <Messages messages={messages}/>
              </div>
        }
      </div>
    );
  }
}

Chat.defaultProps = {
  edit: true,
  messages: [
    {
      id: "-",
      owner: "reactive system",
      body: "We salute you!"
    }
  ],
};

ReactDOM.render(
  <Chat/>,
  document.getElementById("app")
);
