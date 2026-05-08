package com.example.myblog.core.errors;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

// 모든 컨트롤러에서 발생하는 예외를 이 클래스에서 처리 하겠다.
// RuntimeException 이 발생되면 해당 이 파일로 예외 처리가 오게 됨.
@Slf4j
@ControllerAdvice // IoC -> 에러 페이지 찾아가는 기능
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request) {
        log.warn("=== 400 Bad Request 에러 발생 ===");
        log.warn("요청 URL: {}", request.getRequestURL());
        log.warn("에러메시지: {}", e.getMessage());

        request.setAttribute("msg", e.getMessage());
        return "err/400";
    }

    @ExceptionHandler(Exception401.class)
    @ResponseBody
    public String ex401(Exception401 e, HttpServletRequest request) {
        String script = """
            <script>
                alert('%s');
                location.href='/login-form';
            </script>
            """.formatted(e.getMessage());
        return script;
    }


    @ExceptionHandler(Exception403.class)
    @ResponseBody // 파일을 찾지 않고 데이터 반환
    public String ex403(Exception403 e, HttpServletRequest request) {

        String script = """
        <script>
            alert('%s');
            history.back();
        </script>
        """.formatted(e.getMessage());

        return script;
    }

    @ExceptionHandler(Exception404.class)
    public ModelAndView ex404(Exception404 e) {
        log.warn("=== 404 핸들러 작동함 ==="); // 이 로그가 찍히는지 확인!

        // err 폴더 구조 유지
        ModelAndView mav = new ModelAndView("err/404");
        mav.addObject("msg", e.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e, HttpServletRequest request) {
        log.warn("=== 500 Internal Server Error 에러 발생 ===");
        log.warn("요청 URL: {}", request.getRequestURL());
        log.warn("에러메시지: {}", e.getMessage());

        request.setAttribute("msg", e.getMessage());
        return "err/500";
    }

    // 기타 모든 RuntimeException 처리 (최후의 보루)
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.warn("=== 예상치 못한 런타임 에러 발생 ===");
        log.warn("요청 URL: {}", request.getRequestURL());
        log.warn("에러메시지: {}", e.getMessage());

        request.setAttribute("msg", "시스템 오류가 발생했습니다. 관리자에게 문의해주세요");
        return "err/500";
    }
}
