package com.rdfanalyst.streams;

import com.rdfanalyst.rdfengine.RDFEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RDFStreamsService {

    @Autowired
    private RDFEngineService rdfEngineService;

    @RequestMapping("/available-streams")
    public @ResponseBody List<String> availableStreams() {
        return rdfEngineService.getAvailableRunningStreams();
    }

}
