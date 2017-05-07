/** Nav (reusable navbar) */
const Nav = ({ type }) => <nav className="light-blue lighten-3">
  <div className="navbar-wrapper parallax-container">
    <div className="brand-logo center">{type} chat</div>
  </div>
</nav>;

Nav.defaultProps = {
  appName: "SSE Chat",
};

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

const post = (url, owner, body) => fetch(url, {
  method: "post",
  headers: { "content-type": "application/json; charset=UTF-8" },
  body: JSON.stringify({ owner, body, }),
});

const onInput = (owner = "anonymous", { keyCode, target }) => {
  const { value } = target;
  if (keyCode !== 13 || value.trim().length === 0) return;
  post("/api/v1/sse/publish/message", owner, value);
  target.value = "";
  target.focus();
};

const InputContainer = ({ inputRef, owner, onInput }) => <div className="input-field">
  <input type="text"
         id={Date.now()}
         placeholder="enter message"
         ref={input => inputRef = input}
         onKeyDown={e => onInput(owner, e)}/>
</div>;

const MessageSender = ({ owner }) => {
  let inputRef;
  return <InputContainer inputRef={inputRef}
                         onInput={onInput}
                         owner={owner}/>
};

const Messages = ({ messages }) => <ul>
  {
    ! messages
      ? <li>Loading...</li>
      : messages.map((message, id) => <li key={message.id || id}>{message.owner}: {message.body}</li>)
  }
</ul>;

/** sse */

class Chat extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      owner: "",
      edit: props.edit,
      messages: props.messages,
    };
    this.toggleEdit = this.toggleEdit.bind(this);
    this.setUsername = this.setUsername.bind(this);
  }
  connect() {
    return this.source = new EventSource("/api/v1/sse/subscribe/chat");
  }
  subscribe() {
    this.source.addEventListener("chat-message-event", ({ data }) => {
      const { owner, body } = JSON.parse(data);
      this.setState({
        messages: [
          { owner, body },
          ...this.state.messages,
        ],
      });
    });
    this.source.addEventListener("error", e => console.error("sse error", e));
    this.source.addEventListener("open", e => console.log("subscribed on", e.target.url));
  }
  disconnect() {
    if (this.source) {
      source.close();
    }
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
  componentDidMount() {
    this.connect();
    this.subscribe();
  }
  componentWillUnmount () {
    this.disconnect();
  }
  render() {
    const { edit, owner, messages } = this.state;
    return (
      <div className="col s6">
        <Nav type="SSE"/>
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

Chat.defaultProps = {
  edit: true,
  messages: [
    {
      owner: "system",
      body: "We salute you!"
    }
  ],
};

/** reactive */

const onReactiveInput = (owner = "anonymous", { keyCode, target }) => {
  const { value } = target;
  if (keyCode !== 13 || value.trim().length === 0) return;
  post("/api/v1/webflux/publish/message", owner, value);
  target.value = "";
  target.focus();
};

const ReactiveMessageSender = ({ owner }) => {
  let inputRef;
  return <InputContainer inputRef={inputRef}
                         onInput={onReactiveInput}
                         owner={owner}/>
};

class ReactiveChat extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      owner: "",
      edit: props.edit,
      messages: props.messages,
    };
    this.toggleEdit = this.toggleEdit.bind(this);
    this.setUsername = this.setUsername.bind(this);
  }
  connect() {
    this.source = new EventSource("/api/v1/webflux/subscribe/chat");
  }
  subscribe() {
    this.source.addEventListener("message", ({ data }) => {
      const { id, owner, body } = JSON.parse(data);
      this.setState({
        messages: [
          { id, owner, body },
          ...this.state.messages,
        ],
      });
    });
    this.source.addEventListener("error", e => console.error("sse error", e));
    this.source.addEventListener("open", e => console.log("subscribed", e.target.url));
  }
  disconnect() {
    if (this.source) {
      source.close();
    }
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
  componentDidMount() {
    this.connect();
    this.subscribe();
  }
  componentWillUnmount () {
    this.disconnect();
  }
  render() {
    const { edit, owner, messages } = this.state;
    return (
      <div className="col s6">
        <Nav type="Webflux"/>
        {
          edit
            ? <Name setUsername={this.setUsername}/>
            : <div>
                <Header owner={owner}/>
                <ReactiveMessageSender owner={owner}/>
                <Messages messages={messages}/>
              </div>
        }
      </div>
    );
  }
}

ReactiveChat.defaultProps = {
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
  <div className="parallax-container" style={{ padding: '2% 1%' }}>
    <div className="row">
      <Chat/>
      <ReactiveChat/>
    </div>
  </div>,
  document.getElementById("app")
);
