package com.cqx.handler;

import com.cqx.dao.UserRepository;
import com.cqx.model.User;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
/**
 * Created by BG307435 on 2017/10/31.
 */
public class UserHandler {

    private final UserRepository repository;
    WebHandler webHandler = new WebHandler() {
        @Override
        public Mono<Void> handle(ServerWebExchange serverWebExchange) {
            return null;
        }
    };
    HttpHandler httpHandler = new HttpHandler() {
        @Override
        public Mono<Void> handle(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            return null;
        }
    };

    public UserHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> listPeople(ServerRequest request) {
        Flux<User> people = repository.findAll();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(people, User.class);
    }

//    public Mono<ServerResponse> createPerson(ServerRequest request) {
//        Mono<User> person = request.bodyToMono(User.class);
//        return ServerResponse.ok().build(repository.save(person));
//    }

    public Mono<ServerResponse> getPerson(ServerRequest request) {
        int personId = Integer.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<User> personMono = this.repository.findById(personId);
        return personMono
                .flatMap(person -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(person)))
                .switchIfEmpty(notFound);
    }
}