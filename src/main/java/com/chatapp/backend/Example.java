package com.chatapp.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.chatapp.backend.entity.BaseResponse;
import com.chatapp.backend.model.UserRepository;
import com.chatapp.backend.model.user;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@JsonInclude(JsonInclude.Include.NON_NULL) // 若參數是 null 則不顯示 (可省略)
class ExampleData {
    public Integer id;
    public String title;
    public String content;
}

@RestController
public class Example {
    @RestController // ps 底下接的如果不是function，而是class，則要加上這行 定義Class內是一個RESTful API
    @RequestMapping("/")
    @Tag(name = "Example", description = "Example API") // Docs 顯示的描述 (可省略) ，省略會直接顯示Class名稱
    public class Example2 {
        @Autowired
        private UserRepository userRepository;

        // GET 也可以使用 GetMapping("/")
        @RequestMapping(value = "/", method = RequestMethod.GET)
        public List<user> sayHelloGet() {
            return userRepository.findAll();
        }

        // POST 也可以使用 PostMapping("/")
        @RequestMapping(value = "/", method = RequestMethod.POST)
        public String sayHelloPost(
                @RequestParam(value = "firstName", required = false) String firstName, // 若沒有該參數，則 title = null
                @RequestParam(value = "lastName", required = false) String lastName) {
            return "Hello World by POST!";
        }

        // 全部method都可以使用 ， 用來 RESTful API (CRUD)，可在內部細分method
        // 下面是全部method都指向 Test1
        @RequestMapping("/item/{id}")
        public BaseResponse<ExampleData> Test1(
                // @PathVariable: 取得網址上的參數 {id)
                // @RequestParam: 取得網址上GET的參數 ?title=xxx ， required = true 時，沒有該參數會報錯
                @PathVariable(value = "id") Integer id,
                @RequestParam(value = "title", required = false) String title, // 若沒有該參數，則 title = null
                @RequestParam(value = "content", required = false, defaultValue = "預設值") String hasDefault) {

            BaseResponse<ExampleData> response = new BaseResponse<ExampleData>("成功!");
            ExampleData data = new ExampleData();
            data.id = id;
            data.title = title;
            data.content = hasDefault;
            response.data = data;
            return response;
        }
    }
    // 全部method都可以使用 ， 用來 RESTful API (CRUD)，可在內部細分method
    // 下面是全部method都指向 Test1
    //88888888888888

    @RestController // ps 底下接的如果不是function，而是class，則要加上這行 定義Class內是一個RESTful API
    @RequestMapping("/CRUD")
    public class ExampleCRUD {
        ArrayList<String> items = new ArrayList<String>();

        @Operation(summary = "取得該Index的Item", description = "取得該Index的Item") // Docs 顯示的描述 (可省略)
        @GetMapping("/{id}")
        public String findByIndex(@PathVariable String id) {
            try {
                int index;
                index = Integer.parseInt(id);
                return items.get(index);
            } catch (Exception e) {
                return "Not Found";
            }

        }

        @GetMapping("/")
        public ArrayList<String> GetItems() {
            return items;
        }

        @PutMapping("/")
        public String updateBook(
                @RequestBody String item) {
            items.add(item);
            return item;
        }
    }
}
