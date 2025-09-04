# Reproducer for [Duplicate Transfer-Encoding: chunked headers in Quarkus response #49875](https://github.com/quarkusio/quarkus/issues/49875)


### Describe the bug

When making a (big) post request to a Quarkus endpoint, the response contains duplicate Transfer-Encoding: chunked headers. This is not valid per the HTTP specification and can cause issues with clients or proxies that strictly validate responses. In my case it clashes with caddy proxy (using a very strict go lib). I saw this in Quarkus Version 3.26.2 and 3.23.4

### Expected behavior

The response should include the Transfer-Encoding: chunked header only once.

### Actual behavior

```
HTTP/1.1 200 OK
Transfer-Encoding: chunked
Content-Type: application/json
Transfer-Encoding: chunked
```

### How to Reproduce?

Run quarkus test in dev mode that enables live coding using:

```shell script
./mvnw quarkus:test -Dquarkus.test.continuous-testing=enabled
```
