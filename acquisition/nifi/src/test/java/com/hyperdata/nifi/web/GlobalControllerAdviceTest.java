package com.hyperdata.nifi.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hyperdata.nifi.application.dto.FlowUploadRequestDto;
import com.hyperdata.nifi.application.dto.FlowUploadResponseDto;
import com.hyperdata.nifi.application.impl.FlowFileServiceImpl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
class GlobalControllerAdviceTest {
    protected MockMvc mockMvc;
    @MockBean
    private CollectorController collectorController;

    @MockBean
    private FlowFileServiceImpl flowFileService;
    private FlowUploadRequestDto flowUploadRequestDto;
    private FlowUploadResponseDto flowUploadResponseDto;
    private MockMultipartFile firstFile;

    @BeforeEach
    void setup() {

        flowUploadRequestDto = new FlowUploadRequestDto("minifi01");
        flowUploadResponseDto = new FlowUploadResponseDto("fileName", "fileDownloadUri");
        firstFile = new MockMultipartFile("file", "minifi01.png", "image/png", "some xml".getBytes());

        mockMvc = MockMvcBuilders.standaloneSetup(collectorController)
            .setControllerAdvice(GlobalControllerAdvice.class)
            .build();
    }

    @DisplayName("파일 복사 도중에 예외 발생")
    @Test()
    void 파일_복사_도중에_IOException_발생() throws Exception {
        // given

        // when
        when(collectorController.upload(firstFile, flowUploadRequestDto)).thenThrow(new IOException("파일 복사 도중 예외 발생"));
        // when(flowFileService.store(firstFile, flowUploadRequestDto)).thenThrow(new IOException("파일 복사 도중 예외 발생"));

        // then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/collector/upload").file(firstFile))
            .andDo(print())
            .andExpect(result ->
                assertThat(getApiResultExceptionClass(result)).isEqualTo(IOException.class)
            );

    }

    private Class<? extends Exception> getApiResultExceptionClass(MvcResult result) {
        return Objects.requireNonNull(result.getResolvedException()).getClass();
    }
}
