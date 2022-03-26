package io.tbd.tbdex.did_sdk.presentation_exchange;

public class PresentationExchange {
   private String comment;
   private PresentationDefinition presentationDefinition;

   // TODO: add PresentationSubmission

  private PresentationExchange(PresentationDefinition definition) {
    this.presentationDefinition = definition;
  }

  public String comment() {
    return comment;
  }

  public PresentationDefinition presentationDefinition() {
    return presentationDefinition;
  }

  public static class Builder {
    private PresentationExchange instance;

    public Builder(PresentationDefinition definition) {
      this.instance = new PresentationExchange(definition);
    }

    public Builder comment(String comment) {
      this.instance.comment = comment;

      return this;
    }

    public PresentationExchange build() {
      return this.instance;
    }
  }
}
