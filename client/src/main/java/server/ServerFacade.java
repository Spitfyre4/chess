package server;

import com.google.gson.Gson;
import exception.ServerException;
import model.*;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public <T> T makeRequest(String method, String header, String path, Object request, Class<T> responseClass) throws ServerException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.setRequestProperty("authorization", header);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception e) {
            throw new ServerException(e.getMessage(), 500);
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ServerException {
        var status = http.getResponseCode();
        String statusMessage = http.getResponseMessage();
        if (!isSuccessful(status)) {
            throw new ServerException("failure: " + status +" -> " + statusMessage, status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }


    public AuthData register(UserData user) throws ServerException {
        var path = "/user";
        return this.makeRequest("POST", null, path, user, AuthData.class);
    }

    public AuthData login(UserData user) throws ServerException {
        var path = "/session";
        return this.makeRequest("POST", null, path, user, AuthData.class);
    }

    public void joinGame(JoinGameReq req, String authToken) throws ServerException {
        var path = "/game";
        this.makeRequest("PUT", authToken, path, req, Object.class);
    }
    public GamesData listGames(String authToken) throws ServerException {
        var path = "/game";
        return this.makeRequest("GET", authToken, path, null, GamesData.class);
    }
    public GameID createGame(GameData game, String authToken) throws ServerException {
        var path = "/game";
        return this.makeRequest("POST", authToken, path, game, GameID.class);
    }
    public void logout(String authToken) throws ServerException {
        var path = "/session";
        this.makeRequest("DELETE", authToken, path, null, Object.class);
    }

    public void clear() throws ServerException {
        var path = "/db";
        this.makeRequest("DELETE", null, path, null, Object.class);
    }


}