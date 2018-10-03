ENDPOINT: FILTERS
Filters use a MessageSelector under the covers to accept or reject messages from a message
channel. In other words, the MessageSelector provides the business logic for determining
which messages are filtered and those that are not. In this step, you create a
MessageSelector – weeds out File messages where the File name does not start with a
particular string.
