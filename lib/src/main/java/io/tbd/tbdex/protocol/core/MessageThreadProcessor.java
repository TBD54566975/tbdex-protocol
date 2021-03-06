package io.tbd.tbdex.protocol.core;

import java.util.HashMap;
import java.util.Map;

public class MessageThreadProcessor {
  MessageThreadStore messageThreadStore;
  Map<MessageType, MessageProcessor<? extends MessageBody>> messageProcessors = new HashMap<>();

  private MessageThreadProcessor(MessageThreadStore messageThreadStore) {
    this.messageThreadStore = messageThreadStore;
  }

  private void addProcessor(MessageType type, MessageProcessor<? extends MessageBody> processor) {
    this.messageProcessors.put(type, processor);
  }

  /**
   * Adds a message to the message thread. executes any processor registered to the {@link
   * MessageType} of the message being added (if any).
   *
   * @param message - the message being added
   * @return A resulting message generated by processing the message being added. Returns null if
   * the message added results in no new message
   * @throws RuntimeException - if the message being added is invalid
   */
  public Message addMessage(Message message) {
    if (message == null) {
      return null;
    }

    String threadToken = message.threadID();
    Message lastMessage = messageThreadStore.getLastMessage(threadToken);

    MessageType messageType = message.type();

    if (lastMessage == null) {
      // an Ask should always be the first message
      if (messageType != MessageType.Ask) {
        throw new RuntimeException("The first message in a thread can only be an Ask");
      }
    } else {
      if (messageType == MessageType.Ask) {
        throw new RuntimeException("an Ask can only be the first message in a thread");
      }

      // TODO: Check to see if message's sender matches the sender of the last message

      if (!lastMessage.body().isValidReply(messageType)) {
        throw new RuntimeException(
            String.format("%s is not a valid reply to the most recent message [%s] in this thread",
                messageType,
                lastMessage.type())
        );
      }
    }

    this.messageThreadStore.addMessageToThread(message);
    MessageProcessor<? extends MessageBody> processor = messageProcessors.get(message.type());

    if (processor == null) {
      return null;
    }

    Message resultingMessage = processor.process(message);
    this.addMessage(resultingMessage);

    return resultingMessage;
  }

  /**
   * @param encryptedSignedJWM - the encrypted then signed message
   * @return Message
   */
  public Message addMessage(String encryptedSignedJWM) {
    // TODO: decrypt JWM using recipient's private key
    // TODO: decode JWS payload (which is the message itself)
    // TODO: get recipient's DID from the message
    // TODO: resolve the recipient's DID to get DID Doc
    // TODO: use `kid` from JWS header to grab the appropriate public key from DID Doc
    // TODO: verify the JWS

    throw new UnsupportedOperationException("Method not yet implemented");
  }

  public static class Builder {
    private final MessageThreadProcessor instance;

    public Builder(MessageThreadStore messageThreadStore) {
      this.instance = new MessageThreadProcessor(messageThreadStore);
    }

    /**
     * registers the {@link MessageProcessor] provided as the processor that gets called whenever a
     * message of type {@link MessageType} is added to the MessageThreadProcessor using {@link
     * MessageThreadProcessor#addMessage(Message)}. {@link MessageProcessor#process(Message)} only
     * gets called if the message being added is considered to be valid with respect to the current
     * state of the thread
     *
     * @param type      - The {@link MessageType} to register the processor to
     * @param processor - the processor to register
     * @return MessageThreadProcesor.Builder
     */
    public Builder registerProcessor(
        MessageType type,
        MessageProcessor<? extends MessageBody> processor
    ) {
      this.instance.addProcessor(type, processor);

      return this;
    }

    public MessageThreadProcessor build() {
            /* TODO: Decide if we want to keep this
             if (this.instance.messageProcessors.isEmpty()) {
                throw new RuntimeException("please provide at least 1 processor");
             }
            */

      return instance;
    }
  }
}
