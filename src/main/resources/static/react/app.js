(function reactApp() {

  class App extends React.Component {
    constructor() {
      super();
      this.state = {
        messages: []
      };
      this.onEnterPress = this.onEnterPress.bind(this);
      this.handler = this.handler.bind(this);
    }

    handler(content) {
      this.setState({
        messages: [
          content,
          ...this.state.messages,
        ],
      });
    }

    onEnterPress({ keyCode, target }) {
      if (13 !== keyCode) return;
      this.props.sendMessage(target.value);
      target.value = '';
      target.focus();
    }

    componentDidMount() {
      this.props.reconnect(this.handler);
      this.input.focus();
    }

    componentWillUnmount() {
      this.props.disconnect();
    }

    render() {
      const { messages } = this.state;
      const { online, reconnect, disconnect } = this.props;
      return (
        <Container>
          <Header online={online}/>
          <button
            className="btn"
            onClick={() => reconnect(this.handler)}>(re)connect</button>
          &nbsp;
          <button
            className="btn pink"
            onClick={() => disconnect()}>disconnect</button>
          &nbsp;
          <input type="text"
                 placeholder="send message"
                 onKeyDown={this.onEnterPress}
                 ref={input => this.input = input}/>
          <ChildClient messages={messages}/>
        </Container>
      );
    }
  }

  App.propTypes = {
    messages: React.PropTypes.array.isRequired,
  };

  /** header view */
  const Header = ({ owner, online }) => <div>
    <div className="card-title">
      <h5 className="light-blue-text">{owner} {!online && 'dis'}connected!</h5>
    </div>
  </div>;

  Header.propTypes = {
    owner: React.PropTypes.string,
    online: React.PropTypes.bool.isRequired,
  };

  Header.defaultProps = {
    owner: 'badass',
  };

  const Container = props => <div className="container" {...props}/>;

  const ChildClient = ({messages}) => {
    return (
      <ul style={{listStyleType: 'none'}}>
        {messages && messages.map((message, index) =>
          <li key={index}>{message}</li>
        )}
      </ul>
    );
  };

  const {ReactAppWS} = window;

  if (!ReactAppWS
    || !ReactAppWS.reconnect
    || !ReactAppWS.disconnect
    || !ReactAppWS.sendMessage
    || !ReactAppWS.isConnected) {

    const err = 'please provide ReactAppWS object withing required public api...';
    console.error(err);
    document.querySelector('#app').innerHTML = err;
    return;
  }

  const { render } = ReactDOM;

  render(
    <App online={() => window.ReactAppWS.isConnected()} {...window.ReactAppWS}/>,
    document.getElementById('app')
  );

})();
