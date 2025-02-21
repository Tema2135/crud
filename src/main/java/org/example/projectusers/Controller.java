package org.example.projectusers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@EnableJpaRepositories
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/crud")
    public String mainPage(Model model){
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "manePage";
    }

    @PostMapping("/crud/create")
    public String createStudent(@ModelAttribute("name") String name,
                                @ModelAttribute("surname") String surname){
        Student student = new Student();
        student.setName(name);
        student.setSurname(surname);
        studentRepository.save(student);
        return "redirect:/crud";
    }

    @PostMapping("/crud/update")
    public String updateStudent(@RequestParam("id") Long id,
                                @RequestParam("name") String name,
                                @RequestParam("surname") String surname) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student newStudent = student.get();
            if(!name.isEmpty()) {
                newStudent.setName(name);
            }
            if(!surname.isEmpty()) {
            newStudent.setSurname(surname);
            }
            studentRepository.save(newStudent);

        }
        return "redirect:/crud";
    }

    @DeleteMapping("/crud/delete")
    public String deleteStudent(@RequestParam("id") Long id){
        Optional<Student> student= studentRepository.findById(id);
        if(student.isPresent()){
            studentRepository.deleteById(id);
        }
        return "redirect:/crud";
    }


}
