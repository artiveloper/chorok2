package io.chorok2.modules.account.controller;

import io.chorok2.modules.account.domain.Account;
import io.chorok2.modules.account.domain.CurrentAccount;
import io.chorok2.modules.account.dto.ProfileResponse;
import io.chorok2.modules.account.service.AccountService;
import io.chorok2.modules.security.AccountDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"1. Account"})
@RestController
@RequestMapping(value = "/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ProfileResponse> getMyProfile(@CurrentAccount AccountDetails currentAccount) {
        ProfileResponse response = accountService.getProfile(currentAccount.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회한다.")
    @GetMapping
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @ApiOperation(value = "회원 정보 조회", notes = "특정 회원을 조회한다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getAccount(
            @ApiParam(value = "회원 아이디", required = true) @PathVariable Long id,
            @ApiParam(value = "언어", defaultValue = "ko") @RequestParam(defaultValue = "ko") String lang) {
        ProfileResponse response = accountService.getProfile(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
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

