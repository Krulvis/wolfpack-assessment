package org.wolfpack.assessment.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.RedirectView

/**
 * Since we have no front-end, redirect to API documentation
 */
@Controller
@RequestMapping("/")
class MainController {

    @GetMapping("")
    fun index() = RedirectView("/swagger-ui/index.html")
}