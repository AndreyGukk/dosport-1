package ru.dosport.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class UserSportKey implements Serializable {

    static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private long userId;

    @Getter
    @Setter
    private short sportTypeId;

    public UserSportKey(long userId, short sportTypeId) {
        this.userId = userId;
        this.sportTypeId = sportTypeId;
    }
}
