package com.sequenceiq.sdx.api.model;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SdxClusterRequest extends SdxClusterRequestBase {

    @Schema(description = ModelDescriptions.RUNTIME_VERSION)
    private String runtime;

    @Schema(description = ModelDescriptions.RECIPES)
    private Set<SdxRecipe> recipes;

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Set<SdxRecipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<SdxRecipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "SdxClusterRequest{" + "runtime='" + runtime + '\'' + ", recipes=" + recipes + "} " + super.toString();
    }
}
