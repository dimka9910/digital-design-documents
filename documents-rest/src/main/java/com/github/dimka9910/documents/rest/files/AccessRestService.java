package com.github.dimka9910.documents.rest.files;

import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.jpa.entity.user.UserRolesEnum;
import com.github.dimka9910.documents.service.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/access")
public class AccessRestService {

    @Autowired
    AccessService accessService;


    /**
     *
     * @param rw - readWrite or Read
     * @param grant - grant or decline
     * @param file - id of file
     * @param user - id of user who we are modifying access
     * @return
     */
    @PostMapping
    public List<UserDto> grantAccess(@RequestParam boolean rw,
                                     @RequestParam boolean grant,
                                     @RequestParam Long file,
                                     @RequestParam Long user){
        return accessService.modifyFileAccess(file, user, rw, grant);
    }


    @GetMapping
    public Map<String, Object> checkAccess(@RequestParam Long file){
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("read", accessService.chekRAccess(file));
        rtn.put("read_write", accessService.chekRWAccess(file));
        return rtn;
    }

}
