document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('chat-form');
    const messageInput = document.getElementById('message-input');
    const modeSelect = document.getElementById('mode-select');
    const chatContainer = document.getElementById('chat-container');
    const typingIndicator = document.getElementById('typing-indicator');

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const message = messageInput.value.trim();
        const mode = modeSelect.value;

        if (!message) return;

        // Append user message
        appendMessage('You', message);

        // Clear input
        messageInput.value = '';

        try {
            const response = await fetch('/chat', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    message: message,
                    mode: mode
                })
            });

            const data = await response.json();
            appendMessage('Assistant', data.response, mode);
        } catch (error) {
            appendMessage('Assistant', 'Error: Unable to fetch response', mode);
        }
    });

    function appendMessage(sender, text, mode = null) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message');

        let displayName = sender;
        if (sender === 'Assistant' && mode) {
            if (mode === 'sms') displayName = 'SMS Assistant';
            else if (mode === 'mentor') displayName = 'Mentor';
            else displayName = 'Detailing Assistant';
        }

        const content = `<strong>${displayName}:</strong> ${text}`;
        messageDiv.innerHTML = content;
        chatContainer.appendChild(messageDiv);
        chatContainer.scrollTop = chatContainer.scrollHeight;
    }
});
