package com.github.dimka9910.documents.rest.files;

import com.github.dimka9910.documents.dto.restdtos.ManageAccessDto;
import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.service.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/access")
public class AccessRestController {

    @Autowired
    AccessService accessService;

    @PostMapping
    public List<UserDto> grantAccess(@RequestBody @Valid ManageAccessDto manageAccessDto){
        return accessService.modifyFileAccess(manageAccessDto);
    }

    @GetMapping("/{id}")
    public Map<String, Object> checkAccess(@PathVariable Long id){
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("read", accessService.chekRAccess(id));
        rtn.put("read_write", accessService.chekRWAccess(id));
        return rtn;
    }

}
