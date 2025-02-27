#!/usr/bin/env bash

# Define base directory
BASE_DIR=\"src/main/java/com/example/wordquest\"

echo \"Creating folder structure...\"

# Create folder structure
mkdir -p \$BASE_DIR/domain/model\
mkdir -p \$BASE_DIR/domain/repository\
mkdir -p \$BASE_DIR/application/service\
mkdir -p \$BASE_DIR/application/service/impl\
mkdir -p \$BASE_DIR/application/dto\
mkdir -p \$BASE_DIR/application/dto/factory\
mkdir -p \$BASE_DIR/infrastructure/persistence\
mkdir -p \$BASE_DIR/infrastructure/security\
mkdir -p \$BASE_DIR/infrastructure/websocket\
mkdir -p \$BASE_DIR/presentation/controller\
mkdir -p \$BASE_DIR/presentation/websocket\

echo \"Folders created!\"

############################################################
# 1) BaseEntity.java
############################################################
cat <<EOF > \"$BASE_DIR/domain/model/BaseEntity.java\"
package com.example.wordquest.domain.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = \"created_at\", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = \"updated_at\")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
EOF

############################################################
# 2) BaseRepository.java
############################################################
cat <<EOF > \"$BASE_DIR/domain/repository/BaseRepository.java\"
package com.example.wordquest.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
}
EOF

############################################################
# 3) BaseDTO.java
############################################################
cat <<EOF > \"$BASE_DIR/application/dto/BaseDTO.java\"
package com.example.wordquest.application.dto;

import java.io.Serializable;

public abstract class BaseDTO implements Serializable {
}
EOF

############################################################
# 4) BaseDTOFactory.java
############################################################
cat <<EOF > \"$BASE_DIR/application/dto/factory/BaseDTOFactory.java\"
package com.example.wordquest.application.dto.factory;

public interface BaseDTOFactory<E, D> {
    D createDTO(E entity);
}
EOF

############################################################
# 5) BaseService.java
############################################################
cat <<EOF > \"$BASE_DIR/application/service/BaseService.java\"
package com.example.wordquest.application.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
EOF

############################################################
# 6) BaseServiceImpl.java
############################################################
cat <<EOF > \"$BASE_DIR/application/service/impl/BaseServiceImpl.java\"
package com.example.wordquest.application.service.impl;

import com.example.wordquest.domain.repository.BaseRepository;
import com.example.wordquest.application.service.BaseService;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {
    protected final BaseRepository<T, ID> repository;

    protected BaseServiceImpl(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
EOF

############################################################
# 7) BaseController.java
############################################################
cat <<EOF > \"$BASE_DIR/presentation/controller/BaseController.java\"
package com.example.wordquest.presentation.controller;

import com.example.wordquest.application.service.BaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class BaseController<T, ID> {
    protected final BaseService<T, ID> service;

    protected BaseController(BaseService<T, ID> service) {
        this.service = service;
    }

    @GetMapping(\"/{id}\")
    public Optional<T> getById(@PathVariable ID id) {
        return service.findById(id);
    }

    @GetMapping
    public List<T> getAll() {
        return service.findAll();
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return service.save(entity);
    }

    @DeleteMapping(\"/{id}\")
    public void delete(@PathVariable ID id) {
        service.deleteById(id);
    }
}
EOF

############################################################
# 8) Player.java
############################################################
cat <<EOF > \"$BASE_DIR/domain/model/Player.java\"
package com.example.wordquest.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = \"players\")
public class Player extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
EOF

############################################################
# 9) PlayerRepository.java
############################################################
cat <<EOF > \"$BASE_DIR/domain/repository/PlayerRepository.java\"
package com.example.wordquest.domain.repository;

import com.example.wordquest.domain.model.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends BaseRepository<Player, Long> {
}
EOF

############################################################
# 10) PlayerDTO.java
############################################################
cat <<EOF > \"$BASE_DIR/application/dto/PlayerDTO.java\"
package com.example.wordquest.application.dto;

public class PlayerDTO extends BaseDTO {
    private Long id;
    private String username;
    private String email;

    public PlayerDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
EOF

############################################################
# 11) PlayerDTOFactory.java
############################################################
cat <<EOF > \"$BASE_DIR/application/dto/factory/PlayerDTOFactory.java\"
package com.example.wordquest.application.dto.factory;

import com.example.wordquest.application.dto.PlayerDTO;
import com.example.wordquest.domain.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerDTOFactory implements BaseDTOFactory<Player, PlayerDTO> {
    @Override
    public PlayerDTO createDTO(Player entity) {
        return new PlayerDTO(entity.getId(), entity.getUsername(), entity.getEmail());
    }
}
EOF

############################################################
# 12) PlayerServiceImpl.java
############################################################
cat <<EOF > \"$BASE_DIR/application/service/impl/PlayerServiceImpl.java\"
package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.PlayerDTO;
import com.example.wordquest.application.dto.factory.PlayerDTOFactory;
import com.example.wordquest.application.service.BaseService;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerServiceImpl extends BaseServiceImpl<Player, Long> implements BaseService<Player, Long> {
    private final PlayerDTOFactory playerDTOFactory;

    public PlayerServiceImpl(PlayerRepository repository, PlayerDTOFactory playerDTOFactory) {
        super(repository);
        this.playerDTOFactory = playerDTOFactory;
    }

    public PlayerDTO getPlayerDTO(Long id) {
        Optional<Player> player = repository.findById(id);
        return player.map(playerDTOFactory::createDTO).orElse(null);
    }
}
EOF

############################################################
# 13) PlayerController.java
############################################################
cat <<EOF > \"$BASE_DIR/presentation/controller/PlayerController.java\"
package com.example.wordquest.presentation.controller;

import com.example.wordquest.application.dto.PlayerDTO;
import com.example.wordquest.application.service.impl.PlayerServiceImpl;
import com.example.wordquest.domain.model.Player;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(\"/players\")
public class PlayerController extends BaseController<Player, Long> {
    private final PlayerServiceImpl playerService;

    public PlayerController(PlayerServiceImpl playerService) {
        super(playerService);
        this.playerService = playerService;
    }

    @GetMapping(\"/{id}/dto\")
    public PlayerDTO getPlayerDTO(@PathVariable Long id) {
        return playerService.getPlayerDTO(id);
    }
}
EOF

echo \"Project structure and files successfully created!\"
