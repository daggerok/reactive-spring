class Chat extends React.Component {
  constructor() {
    super();
    this.state = { messages: [] };
    this.onInput = this.onInput.bind(this);
  }
  componentDidMount() {
    this.source = new EventSource("/api/v1/chat-messages");
    this.source.addEventListener("chat-message-event", ({ data }) => {
      const { message } = JSON.parse(data);
      this.setState({ messages: [message, ...this.state.messages] });
    });
  }
  onInput({keyCode, target}) {
    if (keyCode === 13) {
      fetch("/api/v1/send-message", {
        method: "post",
        headers: {
          "content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({ message: target.value }),
      });
      target.value = "";
    }
  }
  render() {
    const { messages } = this.state;
    return (
      <div className="container">
        <h3 className="header-panel">{this.props.name || 'chat app'}</h3>
        <div className="card-title">
          <h5>hello anonymous!</h5>
        </div>
        <input placeholder="enter message"
               onKeyDown={e => this.onInput(e)}
               ref={input => this.inputRef = input}/>
        <ul>
          {
            messages
            && messages.map((message, index) =>
              <li key={index}>{message}</li>)
          }
        </ul>
      </div>
    );
  }
}

const Nav = props => <nav className="light-blue lighten-3">
  <div className="navbar-wrapper container">
    <div className="brand-logo center">Chat</div>
  </div>
</nav>;

const Apps = props => <div>
  <Nav/>
  <div>
    {
      !props || !props.children
        ? <div>Loading... (required at least one child)</div>
        : !props.children.length
            ? <props.children.type {...props.children.props} single={true} {...props}>{props.children}</props.children.type>
            : props.children.map((child, key) =>
              React.cloneElement(child, {...props, key, single: false}))
    }
  </div>
</div>;

ReactDOM.render(
  <Apps>
    <Chat/>
  </Apps>,
  document.getElementById("app")
);

// ReactDOM.render(
//   <Apps>
//     <Chat/>
//     <Chat name="yet another chat"/>
//   </Apps>,
//   document.getElementById("app")
// );
