package com.github.taven.springboot3ebean.domain;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

@MappedSuperclass
public class BaseModel extends Model {

    @Id
    long id;

    @Version
    long version;

    @WhenCreated
    Instant whenCreated;

    @WhenModified
    Instant whenModified;

    @WhoCreated
    String whoCreated;

    @WhoModified
    String whoModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Instant getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(Instant whenModified) {
        this.whenModified = whenModified;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }

    public String getWhoModified() {
        return whoModified;
    }

    public void setWhoModified(String whoModified) {
        this.whoModified = whoModified;
    }
}