document.addEventListener('DOMContentLoaded', () => {
    const darkModeToggle = document.getElementById('dark-mode-toggle');
    const form = document.getElementById('chat-form');
    const input = document.getElementById('message-input');
    const mode = document.getElementById('mode-select');
    const chatBox = document.getElementById('chat-box');

    // Load saved theme preference
    if (localStorage.getItem('darkMode') === 'enabled') {
        document.body.classList.add('dark-mode');
        darkModeToggle.classList.add('dark');
    }

    // Dark Mode Toggle
    darkModeToggle.addEventListener('click', () => {
        if (document.body.classList.contains('dark-mode')) {
            document.body.classList.remove('dark-mode');
            darkModeToggle.classList.remove('dark');
            localStorage.setItem('darkMode', 'disabled');
        } else {
            document.body.classList.add('dark-mode');
            darkModeToggle.classList.add('dark');
            localStorage.setItem('darkMode', 'enabled');
        }
    });

    // Form submission handler
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

    // Append a message to the chat box
    function appendMessage(sender, text) {
        const div = document.createElement('div');
        div.className = 'message';
        div.innerHTML = `<strong>${sender}:</strong> ${text}`;
        chatBox.appendChild(div);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    // Get assistant label based on mode
    function getAssistantLabel(mode) {
        switch (mode) {
            case 'sms': return 'SMS Assistant';
            case 'mentor': return 'Mentor Assistant';
            default: return 'Detailing Assistant';
        }
    }

    // Show typing indicator with mode-specific label
    function showTyping() {
        const typingDiv = document.createElement('div');
        typingDiv.id = 'typing';
        typingDiv.className = 'message';
        const modeLabel = getAssistantLabel(mode.value); // Get the mode-specific label
        typingDiv.innerHTML = `<em>${modeLabel} is typing...</em>`;
        chatBox.appendChild(typingDiv);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    // Remove typing indicator
    function removeTyping() {
        const typingDiv = document.getElementById('typing');
        if (typingDiv) typingDiv.remove();
    }
});