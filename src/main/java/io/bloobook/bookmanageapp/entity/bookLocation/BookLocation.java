package io.bloobook.bookmanageapp.entity.bookLocation;

import io.bloobook.bookmanageapp.entity.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
@ToString
@NoArgsConstructor ( access =  AccessLevel.PROTECTED )
@Getter
@Entity
public class BookLocation extends BaseTimeEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String categoryName;

    @Column (nullable = false)
    private String locationCode;

    @Builder
    public BookLocation ( String categoryName, String locationCode ) {
        this.categoryName = categoryName;
        this.locationCode = locationCode;
    }

    public String getLocationInfo () {
        return categoryName + " - " + locationCode;
    }

}
