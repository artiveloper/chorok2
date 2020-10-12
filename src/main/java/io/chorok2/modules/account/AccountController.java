package io.chorok2.modules.account;

import io.chorok2.modules.common.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. Account"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 생성", notes = "회원을 생성한다.")
    @PostMapping
    public Account saveAccount(
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 닉네임", required = true) @RequestParam String nickname) {

        return accountService.createAccount(email, nickname);
    }

    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회한다.")
    @GetMapping
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @ApiOperation(value = "회원 정보 조회", notes = "특정 회원을 조회한다.")
    @GetMapping("/{id}")
    public Account getAccount(
            @ApiParam(value = "회원 아이디", required = true) @PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @ApiOperation(value = "회원 정보 수정", notes = "특정 회원의 정보를 수정한다.")
    @PutMapping
    public Account modifyAccount(
            @ApiParam(value = "회원 아이디", required = true) @RequestParam Long id,
            @ApiParam(value = "회원 닉네임", required = true) @RequestParam String nickname
    ) {

        return accountService.updateAccountInfo(id, nickname);
    }

    @ApiOperation(value = "회원 삭제", notes = "특정 회원을 삭제한다.")
    @PutMapping("/delete")
    public void deleteAccount(
            @ApiParam(value = "회원 아이디", required = true) @RequestParam Long id) {

        accountService.deleteAccount(id);
    }

}
