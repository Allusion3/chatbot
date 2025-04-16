document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('chat-form');
    const input = document.getElementById('message-input');
    const mode = document.getElementById('mode-select');
    const chatBox = document.getElementById('chat-box');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const userText = input.value.trim();
        if (!userText) return;

        appendMessage('You', userText);
        input.value = '';
        showTyping();

        try {
            const res = await fetch('/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    message: userText,
                    mode: mode.value
                })
            });

            const data = await res.json();
            removeTyping();
            appendMessage(getAssistantLabel(mode.value), data.response);
        } catch (err) {
            removeTyping();
            appendMessage('Error', 'Something went wrong.');
        }
    });

    function appendMessage(sender, text) {
        const div = document.createElement('div');
        div.className = 'message';
        div.innerHTML = `<strong>${sender}:</strong> ${text}`;
        chatBox.appendChild(div);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    function getAssistantLabel(mode) {
        switch (mode) {
            case 'sms': return 'SMS Assistant';
            case 'mentor': return 'Mentor Assistant';
            default: return 'Detailing Assistant';
        }
    }

    function showTyping() {
        const typingDiv = document.createElement('div');
        typingDiv.id = 'typing';
        typingDiv.className = 'message';
        typingDiv.innerHTML = `<em>Assistant is typing...</em>`;
        chatBox.appendChild(typingDiv);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    function removeTyping() {
        const typingDiv = document.getElementById('typing');
        if (typingDiv) typingDiv.remove();
    }
});
