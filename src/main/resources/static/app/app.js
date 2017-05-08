const Nav = () => <nav className="light-blue lighten-3">
  <div className="navbar-wrapper parallax-container">
    <div className="brand-logo center">SockJS STOMP Chat</div>
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
    !messages
      ? <li>Loading...</li>
      : messages.map((message, id) => <li id={message.id || id}
                                          key={message.id || id}>{message.owner}: {message.body}</li>)
  }
</ul>;

const MessageSender = ({ sendStompMessage }) => {
  let inputRef;
  return <div className="input-field">
    <input type="text"
           id={Date.now()}
           placeholder="enter message"
           ref={input => inputRef = input}
           onKeyDown={e => sendStompMessage(e)}/>
  </div>;
};

class Chat extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      owner: "",
      connected: false,
      edit: props.edit,
      messages: props.messages,
    };
    this.addMessage = this.addMessage.bind(this);
    this.addMessages = this.addMessages.bind(this);
    this.toggleEdit = this.toggleEdit.bind(this);
    this.setUsername = this.setUsername.bind(this);
    this.subscribe = this.subscribe.bind(this);
    this.sendStompMessage = this.sendStompMessage.bind(this);
  }

  addMessages({ body }) {
    this.setState({ messages: [...JSON.parse(body), ...this.state.messages,], });
  }

  addMessage({ body }) {
    this.setState({ messages: [JSON.parse(body), ...this.state.messages,], });
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

  subscribe() {
    // latest messages subscription
    this.subscription =
      this.stompClient.subscribe("/queue/messages.subscribe", this.addMessages);
    this.stompClient.send("/app/api/v1/messages", {}, JSON.stringify(25));
    // new message subscription
    this.subscription
      = this.stompClient.subscribe("/queue/message.subscribe", this.addMessage);
    this.setState({ connected: true });
  }

  messageWith(body) {
    return JSON.stringify({ owner: this.state.owner, body, });
  }

  sendStompMessage({ keyCode, target }) {
    const { value } = target;
    if (13 !== keyCode || !value || !value.trim().length) return;
    this.stompClient.send("/app/api/v1/message/publish", {}, this.messageWith(value));
    target.value = "";
  }

  componentDidMount() {
    this.sockjs = new SockJS("/stomp-chat-endpoint");
    this.stompClient = Stomp.over(this.sockjs);
    if (!this.props.debug) this.stompClient.debug = this.props.debug;
    this.stompClient.heartbeat.outgoing = this.stompClient.heartbeat.incoming = 290000;
    this.stompClient.connect("guest", "guest", this.subscribe);
  }

  componentWillUnmount() {
    if (this.subscription && this.subscription.unsubscribe) this.subscription.unsubscribe();
    if (this.client && this.client.disconnect) this.client.disconnect();
    this.setState({ connected: false });
  }

  render() {
    const { edit, owner, messages } = this.state;
    return (
      <div className="container" style={{ padding: "2% 1%" }}>
        <Nav/>
        {
          edit
            ? <Name setUsername={this.setUsername}/>
            : <div>
            <Header owner={owner}/>
            <MessageSender sendStompMessage={this.sendStompMessage}/>
            <Messages messages={messages}/>
          </div>
        }
      </div>
    );
  }
}

Chat.defaultProps = {
  debug: false,
  history: 25,
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

