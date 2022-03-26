package io.tbd.tbdex.did_sdk.presentation_exchange;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Presentation Definitions are objects that articulate what proofs a Verifier requires. These help
 * the Verifier to decide how or whether to interact with a Holder. Presentation Definitions are
 * composed of inputs, which describe the forms and details of the proofs they require, and optional
 * sets of selection rules, to allow Holders flexibility in cases where different types of proofs
 * may satisfy an input requirement.
 * More info available <a href="https://identity.foundation/presentation-exchange/#presentation-definition">here</a>
 */
public class PresentationDefinition {
  private String id;

  @SerializedName("input_descriptors")
  private List<InputDescriptor> inputDescriptors;

  private String name;
  private String purpose;

  public String id() {
    return id;
  }

  public List<InputDescriptor> inputDescriptors() {
    return inputDescriptors;
  }

  public String name() {
    return name;
  }

  public String purpose() {
    return purpose;
  }

  private PresentationDefinition(String id, InputDescriptor ... inputDescriptors) {
    this.id = id;
    this.inputDescriptors = List.of(inputDescriptors);
  }

  public static class Builder {
    private PresentationDefinition instance;

    Builder(String id, InputDescriptor ... inputDescriptors) {
      this.instance = new PresentationDefinition(id, inputDescriptors);
    }

    public Builder name(String name) {
      this.instance.name = name;

      return this;
    }

    public Builder purpose(String purpose) {
      this.instance.purpose = purpose;

      return this;
    }

    public PresentationDefinition build() {
      // TODO: add validation
      return this.instance;
    }
  }

}
