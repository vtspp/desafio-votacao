package br.com.cooperativismo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class VoteResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID voteId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> messageError;

    public VoteResponse(UUID voteId) {
        this.voteId = voteId;
    }

    public VoteResponse(List<ObjectError> errors) {
        if (!errors.isEmpty()) {
            this.messageError = new ArrayList<>(errors.size());
            errors.forEach(error -> messageError.add(error.getDefaultMessage()));
        }
    }
}