ifdef host
        host := $(host)
else
        host := "127.0.0.1"
endif

ifdef port
        port := $(port)
else
        port := 8080
endif

server:
	@echo "Start server with host: $(host), port: $(port) ..."
	sbt "runMain fm.Server --host $(host) --port $(port)"
client: 
	@echo "Start client in connecting to host: $(host), port: $(port) ..."
	sbt "runMain fm.Client --host $(host) --port $(port)"
clean:
	rm -r target
	rm -r project
