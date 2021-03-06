package io.tbd.tbdex.pfi_mock_impl.processors;

import com.google.inject.AbstractModule;
import io.tbd.tbdex.pfi_mock_impl.payments.PaymentProcessor;
import io.tbd.tbdex.pfi_mock_impl.payments.circle.CircleClient;
import io.tbd.tbdex.pfi_mock_impl.payments.circle.RealCircleClient;
import io.tbd.tbdex.pfi_mock_impl.store.HibernateMessageThreadStore;
import io.tbd.tbdex.protocol.core.MessageThreadProcessor;
import io.tbd.tbdex.protocol.core.MessageThreadStore;
import io.tbd.tbdex.protocol.core.MessageType;

public class ProcessorModule extends AbstractModule {
  @Override protected void configure() {
    MessageThreadStore threadStore = new HibernateMessageThreadStore();
    CircleClient circleClient = new RealCircleClient();
    MessageThreadProcessor processor = new MessageThreadProcessor.Builder(threadStore)
        .registerProcessor(MessageType.Ask,
            new AskProcessor())
        .registerProcessor(MessageType.Close,
            new CloseProcessor())
        .registerProcessor(MessageType.OfferAccept,
            new OfferAcceptProcessor())
        .registerProcessor(MessageType.SettlementDetails,
            new SettlementDetailsProcessor(new PaymentProcessor(circleClient)))
        .build();

    bind(MessageThreadProcessor.class).toInstance(processor);
    bind(MessageThreadStore.class).toInstance(threadStore);
    bind(CircleClient.class).toInstance(circleClient);
  }
}
