package org.sopt.server.api;

import org.sopt.server.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ds on 2018-11-24.
 */

@RestController
public class StoreController {

    @GetMapping("/users/{userIdx}")
    public ResponseEntity getUsers(@PathVariable(value = "userIdx") final int userIdx) {
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity saveUsers(@RequestBody final User user) {
        return new ResponseEntity(null, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userIdx}")
    public ResponseEntity updateUser(
            @PathVariable(value = "userIdx") final int userIdx,
            @RequestBody final User user) {
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{userIdx}")
    public ResponseEntity deleteUser(
            @PathVariable(value = "userIdx") final int userIdx) {
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}
