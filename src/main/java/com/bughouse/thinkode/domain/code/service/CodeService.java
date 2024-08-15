package com.bughouse.thinkode.domain.code.service;

import com.bughouse.thinkode.domain.code.request.CodeRequest;
import com.bughouse.thinkode.domain.code.status.CodeStatus;
import com.bughouse.thinkode.exception.CodeExeption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Slf4j
public class CodeService {

    public void checkCode(CodeRequest codeRequest) {
        try {
            Process process = getProcess(codeRequest);

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String error = errorReader.readLine();
            if (error != null) {
                throw new CodeExeption(CodeStatus.ERROR, "코드 실행 중 오류가 발생했습니다.");
            }

            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            for (String line : codeRequest.getOutput()) {
                String output = outputReader.readLine();
                if (output == null || !output.equals(line)) {
                    throw new CodeExeption(CodeStatus.WRONG_ANSWER, "출력값이 일치하지 않습니다.");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new CodeExeption(CodeStatus.ERROR, "코드 실행 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            if(e instanceof CodeExeption) {
                throw (CodeExeption) e;
            }
            throw new CodeExeption(CodeStatus.ERROR, "정의되지 않는 오류가 발생했습니다. 관리자에게 문의하세요");
        }
    }

    private static Process getProcess(CodeRequest codeRequest) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", "-c", codeRequest.getCode());
        processBuilder.redirectErrorStream(false);
        Process process = processBuilder.start();

        // 입력값들 입력
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            for (String input : codeRequest.getInput()) {
                writer.write(input);
                writer.newLine();
            }
        }
        return process;
    }

}
