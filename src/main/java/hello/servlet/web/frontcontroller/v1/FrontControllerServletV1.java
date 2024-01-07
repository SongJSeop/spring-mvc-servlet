package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")  // v1 하위의 모든 요청은 이 서블릿에서 받아들인다.
public class FrontControllerServletV1 extends HttpServlet {

    private final Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // 요청이 들어오면 이 서블릿이 호출되고, 요청 URI를 확인한다.
            String requestURI = request.getRequestURI();

            // 그리고 그 URI에 해당하는 컨트롤러를 찾아서 호출한다.
            ControllerV1 controller = controllerMap.get(requestURI);
            if (controller == null) {  // 만약 해당하는 컨트롤러가 없다면 에러
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 404
                return;
            }

            // 그리고 그 컨트롤러가 뷰의 경로를 반환한다.
            controller.process(request, response);
    }
}
