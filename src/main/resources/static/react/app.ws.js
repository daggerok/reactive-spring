'use strict';

window.ReactAppWS = (function ReactAppWS() {

  const
    WS_CONNECT_ENDPOINT = '/stomp-chat-endpoint',
    // SEND_NAME_URI = '/app/api/v1/command/consume-stomp-message',
    // SEND_NAME_URI = '/app/api/v1/command/send-stomp-message',
    SEND_NAME_URI = '/app/api/v1/query/subscribe/get-sync-messages',
    GREETINGS_TOPIC = '/topic/test-messages',
    PING = 30000;

  let
    stompClient = null,
    online = false,
    latestHandler;

  function createClient() {
    const socket = new SockJS(WS_CONNECT_ENDPOINT);
    return Stomp.over(socket);
  }

  function setConnected(connected) {
    online = connected;
  }

  function connect(handler) {

    latestHandler = handler;
    stompClient = createClient();
    stompClient.heartbeat.outgoing = PING;
    stompClient.heartbeat.incoming = PING;
    stompClient.connect(
      // some headers:
      {user: 'max'},

      function onConnect(frame) {
        setConnected(true);
        /*console.log('Connected: ' + frame);*/
        stompClient.subscribe(GREETINGS_TOPIC, function subscribeTopicGreetings(event) {
          const { body } = event;
          latestHandler(body);
        });
      },

      // disconnection (error) handler
      function onDisconnect(message) {
        console.warn('disconnecting...');
        console.warn(message);
        online = false;
        stompClient = null;
      }
    );
  }

  // public API

  function sendMessage(name) {
    const sendName = () =>
      stompClient.send(SEND_NAME_URI, {}, JSON.stringify({'name': name}));
    if (online && stompClient && isConnected()) {
      sendName();
    } else {
      connect(latestHandler);
      setTimeout(function () {
        sendName();
      }, 1500);
    }
  }

  function disconnect() {
    if (stompClient) {
      stompClient.disconnect();
    }
    setConnected(false);
  }

  function reconnect(handler) {
    disconnect();
    connect(handler);
  }

  function isConnected() {
    return stompClient && stompClient.connected;
  }

  return {
    isConnected,
    sendMessage,
    disconnect,
    reconnect,
  };

})();
