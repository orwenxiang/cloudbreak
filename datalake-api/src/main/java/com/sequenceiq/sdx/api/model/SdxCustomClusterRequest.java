package com.sequenceiq.sdx.api.model;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.image.ImageSettingsV4Request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SdxCustomClusterRequest extends SdxClusterRequestBase {

    @ApiModelProperty(ModelDescriptions.IMAGE_SETTINGS)
    private ImageSettingsV4Request imageSettingsV4Request;

    public ImageSettingsV4Request getImageSettingsV4Request() {
        return imageSettingsV4Request;
    }

    public void setImageSettingsV4Request(ImageSettingsV4Request imageSettingsV4Request) {
        this.imageSettingsV4Request = imageSettingsV4Request;
    }

    public String getRuntime() {
        return null;
    }

    @Override
    public String toString() {
        return "SdxCustomClusterRequest{" + "base=" + super.toString() +
                ", imageSettingsV4Request=" + imageSettingsV4Request +
                '}';
    }

    public Pair<SdxClusterRequest, ImageSettingsV4Request> convertToPair() {
        SdxClusterRequest newRequest = new SdxClusterRequest();
        copyTo(newRequest);

        return new Pair<SdxClusterRequest, ImageSettingsV4Request>() {
            @Override
            public SdxClusterRequest getLeft() {
                return newRequest;
            }

            @Override
            public ImageSettingsV4Request getRight() {
                return getImageSettingsV4Request();
            }

            @Override
            public ImageSettingsV4Request setValue(ImageSettingsV4Request value) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
