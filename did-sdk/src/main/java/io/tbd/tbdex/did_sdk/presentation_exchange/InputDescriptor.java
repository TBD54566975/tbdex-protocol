package io.tbd.tbdex.did_sdk.presentation_exchange;

/**
 * Input Descriptors are objects used to describe the information a Verifier requires of a Holder. All Input Descriptors MUST be satisfied, unless otherwise specified by a Feature.
 * More info <a href="https://identity.foundation/presentation-exchange/#input-descriptor">here</a>
 */
public class InputDescriptor {
  private String id;
  private String name;
  private String purpose;
  private InputDescriptorConstraint constraints;
}
