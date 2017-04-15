package daggerok.svc;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BarService {

    final FooService fooService;
}
