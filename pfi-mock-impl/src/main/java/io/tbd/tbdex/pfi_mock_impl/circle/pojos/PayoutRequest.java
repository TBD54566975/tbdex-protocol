package io.tbd.tbdex.pfi_mock_impl.circle.pojos;

public class PayoutRequest {
  public Source source;
  public Destination destination;
  public Amount amount;
  public Metadata metadata;
  public String idempotencyKey;
}