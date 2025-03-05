package com.ilvan.media_collection.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String name;

    public enum Values {
        ADMIN(1L),
        BASIC(2L);

        long roleid;

        Values(long roleid) {
            this.roleid = roleid;
        }

        public long getRoleid() {
            return roleid;
        }
    }
}
