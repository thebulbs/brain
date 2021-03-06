package com.thoughtworks.bulb.domain;

import com.thoughtworks.reference.domain.BulbReference;
import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
@Builder
@Getter
// FIXME: can we do this without setters?
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bulb {

    @GraphId
    private Long id;

    private String title;

    private String summary;

    @NonNull
    @Index(unique = true)
    private String uuid;

    @NonNull
    @Index
    private String userId;

    @Relationship(type = "LINK_TO")
    private Set<Bulb> links;

    @Relationship(type = "BACKED_BY_REFERENCE")
    private Set<BulbReference> references;

}
