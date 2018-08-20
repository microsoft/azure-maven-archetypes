package $package;

import com.microsoft.azure.sdk.iot.device.*;
import com.microsoft.azure.sdk.iot.device.transport.IotHubConnectionStatus;

public class App {
    private static MessageCallbackMqtt msgCallback = new MessageCallbackMqtt();
    private static EventCallback eventCallback = new EventCallback();
    private static final String INPUT_NAME = "input1";
    private static final String OUTPUT_NAME = "output1";

    protected static class EventCallback implements IotHubEventCallback {
        @Override
        public void execute(IotHubStatusCode status, Object context) {
            if (context instanceof Message) {
                System.out.println("Send message with status: " + status.name());
            } else {
                System.out.println("Invalid context passed");
            }
        }
    }

    protected static class MessageCallbackMqtt implements MessageCallback {
        private int counter = 0;

        @Override
        public IotHubMessageResult execute(Message msg, Object context) {
            this.counter += 1;

            System.out.println(
                    String.format("Received message %d: %s",
                            this.counter, new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET)));
            if (context instanceof ModuleClient) {
                ModuleClient client = (ModuleClient) context;
                client.sendEventAsync(msg, eventCallback, msg, App.OUTPUT_NAME);
            }
            return IotHubMessageResult.COMPLETE;
        }
    }

    protected static class ConnectionStatusChangeCallback implements IotHubConnectionStatusChangeCallback {

        @Override
        public void execute(IotHubConnectionStatus status,
                            IotHubConnectionStatusChangeReason statusChangeReason,
                            Throwable throwable, Object callbackContext) {
            String statusStr = "Connection Status: %s";
            switch (status) {
                case CONNECTED:
                    System.out.println(String.format(statusStr, "Connected"));
                    break;
                case DISCONNECTED:
                    System.out.println(String.format(statusStr, "Disconnected"));
                    if (throwable != null) {
                        throwable.printStackTrace();
                    }
                    System.exit(1);
                    break;
                case DISCONNECTED_RETRYING:
                    System.out.println(String.format(statusStr, "Retrying"));
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
            System.out.println("Start to create client with MQTT protocol");
            ModuleClient client = ModuleClient.createFromEnvironment(protocol);
            System.out.println("Client created");
            client.setMessageCallback(App.INPUT_NAME, msgCallback, client);
            client.registerConnectionStatusChangeCallback(new ConnectionStatusChangeCallback(), null);
            client.open();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
