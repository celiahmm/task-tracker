.PHONY: build clean install

build:
	mvn clean package
	chmod +x task-cli

install: build
	sudo ln -sf $(PWD)/task-cli /usr/local/bin/task-cli

clean:
	mvn clean
	rm -f tasks.json
