package io.tbd.tbdex.did_sdk.resolver;

public interface DIDResolver {
    DIDResolutionResult resolve(String did);
}
