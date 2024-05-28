package example.user.api;

import example.auth.UserId;
import example.common.ApiResponseUtil;
import example.common.BaseResponse;
import example.common.Constants;
import example.message.SuccessMessage;
import example.user.api.request.UserReissueRequest;
import example.user.api.request.UserSignInRequest;
import example.user.api.request.UserSignUpRequest;
import example.user.api.response.UserAuthResponse;
import example.user.facade.AuthFacade;
import example.user.service.response.UserAuthServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@Controller
public class AuthController {
    private final AuthFacade authFacade;

    @PostMapping("/users/signup")
    public ResponseEntity<BaseResponse<?>> signUp(@RequestHeader(Constants.AUTHORIZATION) final String accessToken,
                                                  @RequestBody final UserSignUpRequest request) {
        final UserAuthServiceResponse response = authFacade.signUp(request.toServiceDto(accessToken));
        return ApiResponseUtil.success(SuccessMessage.CREATED, UserAuthResponse.from(response));
    }

    @PostMapping("/users/signin")
    public ResponseEntity<BaseResponse<?>> signIn(@RequestHeader(Constants.AUTHORIZATION) final String accessToken,
                                                  @RequestBody final UserSignInRequest request) {
        final UserAuthServiceResponse response = authFacade.signIn(request.toServiceDto(accessToken));
        return ApiResponseUtil.success(SuccessMessage.CREATED, UserAuthResponse.from(response));
    }

    @PostMapping("/users/reissue")
    public ResponseEntity<BaseResponse<?>> reissue(@RequestHeader(Constants.AUTHORIZATION) final String refreshToken,
                                                   @RequestBody final UserReissueRequest request) {
        final UserAuthServiceResponse response = authFacade.reissue(request.toServiceDto(refreshToken));
        return ApiResponseUtil.success(SuccessMessage.CREATED, UserAuthResponse.from(response));
    }

    @PatchMapping("/users/signout")
    public ResponseEntity<BaseResponse<?>> signOut(@UserId final Long userId) {
        authFacade.signOut(userId);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }

    @DeleteMapping("/users/withdrawal")
    public ResponseEntity<BaseResponse<?>> withdrawal(@UserId final Long userId) {
        authFacade.withdrawal(userId);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }
}
