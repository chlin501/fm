ifdef host
        host := $(host)
else
        host := "localhost"
endif

ifdef port
        port := $(port)
else
        port := 8080
endif

server:
	@echo "run server with host: $(host), port: $(port) ..."
	sbt "runMain fm.Server --host $(host) --port $(port)"
