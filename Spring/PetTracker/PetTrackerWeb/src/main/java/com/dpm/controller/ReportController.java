package com.dpm.controller;

import com.dpm.model.Pet;
import com.dpm.model.Prescription;
import com.dpm.model.Report;
import com.dpm.service.PetService;
import com.dpm.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danielpm.dev
 */
@Controller
public class ReportController {

    private final ReportService reportService;
    private final PetService petService;
    //private final OwnerService ownerService;

    public ReportController(PetService petService, ReportService reportService) {
        this.petService = petService;
        this.reportService = reportService;
    }

    @GetMapping("/pet/{id}/reports")
    public String showReports(@PathVariable Long id, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                Pet pet = petService.getPetById(token, id);
                if (pet == null) {
                    model.addAttribute("error", "Mascota no encontrada.");
                    return "mascotas";
                }

                List<Report> reportList = reportService.getReports(token, id);
                pet.setReportList(reportList);

                // Agregar atributos al modelo
                model.addAttribute("pet", pet);
                //model.addAttribute("newReport", new Report());

                return "reports"; // Nombre de la plantilla vaccines.html
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar los informes médicos: " + e.getMessage());
                model.addAttribute("pet", petService.getPetById(token, id)); // Asegúrate de incluir la mascota si es posible
                return "reports"; // Mantenerse en la página con el error, no redirigir
            }
        }

        return "redirect:/mascotas"; // Redirigir si no está autenticado
    }

    @PostMapping("/pet/{id}/reports/add")
    public String addReport(@PathVariable Long id,
                            @RequestParam("reportDate") LocalDate reportDate,
                            @RequestParam("clinic") String clinic,
                            @RequestParam("reason") String reason,
                            @RequestParam(value = "medName[]", required = false) List<String> medNames,
                            @RequestParam(value = "startDate[]", required = false) List<LocalDate> startDates,
                            @RequestParam(value = "endDate[]", required = false) List<LocalDate> endDates,
                            @RequestParam(value = "frequency[]", required = false) List<String> frequencies,
                            Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                Pet pet = petService.getPetById(token, id);
                Report newReport = new Report();
                newReport.setPet(pet);
                newReport.setReportDate(reportDate);
                newReport.setClinic(clinic);
                newReport.setReason(reason);

                // Construir la lista de prescripciones desde los parámetros
                List<Prescription> prescriptions = new ArrayList<>();
                if (medNames != null && !medNames.isEmpty()) {
                    for (int i = 0; i < medNames.size(); i++) {
                        Prescription prescription = new Prescription();
                        prescription.setMedName(medNames.get(i));
                        prescription.setStartDate(startDates.get(i));
                        prescription.setEndDate(endDates.get(i));
                        prescription.setFrequency(frequencies.get(i));
                        prescriptions.add(prescription);
                    }
                }
                newReport.setPrescriptionList(prescriptions);

                reportService.saveReport(token, newReport, id);
                model.addAttribute("success", "Informe añadido correctamente.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al añadir el informe: " + e.getMessage());
            }
            Pet pet = petService.getPetById(token, id);
            model.addAttribute("pet", pet);
            return "reports";
        }
        return "redirect:/mascotas";
    }

    @PostMapping("/pet/{id}/reports/delete/{reportId}")
    public String deleteReport(@PathVariable Long id, @PathVariable Long reportId, Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                reportService.deleteReportById(token, reportId);
                model.addAttribute("success", "Informe eliminado correctamente.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al eliminar el informe: " + e.getMessage());
            }
            Pet pet = petService.getPetById(token, id);
            model.addAttribute("pet", pet);
            model.addAttribute("newReport", new Report());
            return "reports";
        }
        return "redirect:/mascotas";
    }

    @PostMapping("/pet/{id}/reports/{reportId}/prescriptions/delete/{prescriptionId}")
    public String deletePrescription(@PathVariable Long id, @PathVariable Long reportId, @PathVariable Long prescriptionId,
                                     Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwtToken");
        Boolean isAuthenticated = token != null && !token.isEmpty();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            try {
                reportService.deletePrescriptionById(token, prescriptionId);
                model.addAttribute("success", "Prescripción eliminada correctamente.");
            } catch (Exception e) {
                model.addAttribute("error", "Error al eliminar la prescripción: " + e.getMessage());
            }
            Pet pet = petService.getPetById(token, id);
            model.addAttribute("pet", pet);
            model.addAttribute("newReport", new Report());
            return "reports";
        }
        return "redirect:/mascotas";
    }
}

