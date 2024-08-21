package com.bughouse.thinkode.domain.code.service;

import com.bughouse.thinkode.domain.code.request.CodeRequest;
import com.bughouse.thinkode.domain.code.response.CodeResponse;
import com.bughouse.thinkode.domain.code.status.CodeStatus;
import com.bughouse.thinkode.exception.CodeExeption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Slf4j
public class CodeService {

    public CodeResponse checkCode(CodeRequest codeRequest) {
        try {
            long beforeTime = System.currentTimeMillis();
            Process process = getProcess(codeRequest);
            // TODO: 메모리 사용량이 코드와 관계 없이 4000~6000 kb 정도로 나옴
            long memory = getMemory(process);

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String error = errorReader.readLine();
            if (error != null) {
                throw new CodeExeption(CodeStatus.ERROR, "코드 실행 중 오류가 발생했습니다.");
            }

            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            for (String line : codeRequest.getOutput()) {
                String output = outputReader.readLine();
                if (output == null || !output.trim().equals(line.trim())) {
                    throw new CodeExeption(CodeStatus.WRONG, "출력값이 일치하지 않습니다.");
                }
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new CodeExeption(CodeStatus.ERROR, "코드 실행 중 오류가 발생했습니다.");
            }
            long afterTime = System.currentTimeMillis();
            long time = afterTime - beforeTime;

            CodeResponse response = new CodeResponse();
            response.setStatus(CodeStatus.SUCCESS);
            response.setTime(time);
            response.setMemory(memory);
            response.setMessage("정답입니다.");
            log.info(response.toString());
            return response;
        } catch (Exception e) {
            if (e instanceof CodeExeption) {
                throw (CodeExeption) e;
            }
            throw new RuntimeException(e);
//            throw new CodeExeption(CodeStatus.ERROR, "정의되지 않는 오류가 발생했습니다. 관리자에게 문의하세요");
        }
    }

    private static long getMemory(Process process) throws IOException, InterruptedException {
        long pid = process.pid();
        String[] command = {"bash", "-c", "ps -o rss= -p " + pid};
        ProcessBuilder memoryUsageBuilder = new ProcessBuilder(command);
        Process memoryUsageProcess = memoryUsageBuilder.start();

        // 메모리 사용량 출력 읽기
        long memory;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(memoryUsageProcess.getInputStream()))) {
            String memoryUsage = reader.readLine();
            if (memoryUsage == null) {
                memory = -1;
            } else {
                memory = Long.parseLong(memoryUsage.trim());
            }
        }
        memoryUsageProcess.waitFor();
        return memory;
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
