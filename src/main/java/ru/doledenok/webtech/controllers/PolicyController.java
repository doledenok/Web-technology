package ru.doledenok.webtech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.doledenok.webtech.DAO.PositionDAO;
import ru.doledenok.webtech.DAO.impl.PolicyDAOImpl;
import ru.doledenok.webtech.DAO.impl.PositionDAOImpl;
import ru.doledenok.webtech.models.Policy;
import ru.doledenok.webtech.models.Position;


import java.util.List;

@Controller
public class PolicyController {

    @Autowired
    private final PolicyDAOImpl policyDAO = new PolicyDAOImpl();

    @Autowired
    private final PositionDAO positionDAO = new PositionDAOImpl();

    @GetMapping("/policies")
    public String policiesListPage(Model model) {
        List<Policy> policies = (List<Policy>) policyDAO.getAll();
        model.addAttribute("policies", policies);
        return "policies";
    }

    @GetMapping("/policy")
    public String policyPage(@RequestParam(name = "policyId") Long policyId, Model model) {
        Policy policy = policyDAO.getById(policyId);

        if (policy == null) {
            model.addAttribute("error_msg", "В базе нет проекта с ID = " + policyId);
            return "errorPage";
        }

        model.addAttribute("policy", policy);
        model.addAttribute("policyService", policyDAO);
        return "policy";
    }

    @PostMapping("/savePolicy")
    public String savePolicyPage(@RequestParam(name = "policyId", required = false) Long policyId,
                                 @RequestParam(name = "policyPositionName", required = false) String policyPositionName,
                                 @RequestParam(name = "policySum", required = false) Long policySum,
                                 @RequestParam(name = "policyRegularity", required = false) String policyRegularity,
                                 @RequestParam(name = "policyType", required = false) String policyType,
                                 @RequestParam(name = "policyDescription", required = false) String policyDescription,
                                 Model model) {

        Policy policy;
        Position position = null;
        if (policyPositionName != null) {
            position = positionDAO.getPositionByName(policyPositionName);
            if (position == null) {
                model.addAttribute("error_msg", "В базе нет должности " + policyPositionName);
                return "errorPage";
            }
        }
        if (policyId == null) {
            // создаём новую политику
            // FIXME: добавить обработку добавления роли в проекте
            policy = new Policy((Long) (Number) (policyDAO.getAll().size() + 1L), position, null, policySum, policyRegularity, policyType, policyDescription);
            policyDAO.save(policy);
        }
        else {
            policy = policyDAO.getById(policyId);
            if (policy == null) {
                model.addAttribute("error_msg", "В базе нет политики с ID = " + policyId);
                return "errorPage";
            }
            policy.setPosition(position);
            policy.setSum(policySum);
            policy.setRegularity(policyRegularity);
            policy.setType(policyType);
            policy.setDescription(policyDescription);
            policyDAO.update(policy);
        }
        return String.format("redirect:/policy?policyId=%d", policy.getId());
    }

    @GetMapping("/editPolicy")
    public String editPolicyPage(@RequestParam(name = "policyId", required = false) Long policyId, Model model) {
        if (policyId == null) {
            model.addAttribute("policy", new Policy());
            return "editPolicy";
        }

        Policy policy = policyDAO.getById(policyId);

        if (policy == null) {
            model.addAttribute("error_msg", "В базе нет политики с ID = " + policyId);
            return "errorPage";
        }

        model.addAttribute("policy", policy);
        return "editPolicy";
    }

    @PostMapping("/removePolicy")
    public String removePolicyPage(@RequestParam(name = "policyId") Long policyId) {
        policyDAO.deleteById(policyId);
        return "redirect:/policies";
    }
}
