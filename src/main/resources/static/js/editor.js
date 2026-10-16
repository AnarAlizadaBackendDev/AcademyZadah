// Admin editors: Markdown editor with drag & drop image upload, and cover image drop zone.
(function () {
    const csrfToken = document.querySelector('meta[name=_csrf]')?.content;
    const csrfHeader = document.querySelector('meta[name=_csrf_header]')?.content;

    async function uploadImage(file) {
        const body = new FormData();
        body.append('image', file);
        const headers = csrfHeader ? {[csrfHeader]: csrfToken} : {};
        const res = await fetch('/admin/uploads/image', {method: 'POST', body, headers});
        const json = await res.json();
        if (!res.ok) throw new Error(json.error || 'Upload failed');
        return json.url;
    }

    document.querySelectorAll('textarea[data-markdown]').forEach(textarea => {
        new EasyMDE({
            element: textarea,
            spellChecker: false,
            autoDownloadFontAwesome: true,
            minHeight: '360px',
            uploadImage: true,
            imageAccept: 'image/png, image/jpeg, image/gif, image/webp',
            imageMaxSize: 10 * 1024 * 1024,
            imageUploadFunction: (file, onSuccess, onError) =>
                uploadImage(file).then(onSuccess).catch(e => onError(e.message)),
            renderingConfig: {codeSyntaxHighlighting: !!window.hljs},
            toolbar: ['bold', 'italic', 'heading', '|', 'code', 'quote', 'unordered-list', 'ordered-list', 'table', '|',
                'link', 'upload-image', '|', 'preview', 'side-by-side', 'fullscreen', '|', 'guide'],
        });
    });

    document.querySelectorAll('[data-cover-drop]').forEach(zone => {
        const hidden = zone.querySelector('input[type=hidden]');
        const fileInput = zone.querySelector('input[type=file]');
        const img = zone.querySelector('img');
        const label = zone.querySelector('span');
        const original = label.textContent;

        async function handle(file) {
            if (!file) return;
            label.textContent = '…';
            try {
                const url = await uploadImage(file);
                hidden.value = url;
                img.src = url;
                img.classList.remove('hidden');
                label.textContent = original;
            } catch (e) {
                label.textContent = e.message;
            }
        }

        zone.addEventListener('click', e => { if (e.target !== fileInput) fileInput.click(); });
        fileInput.addEventListener('change', () => handle(fileInput.files[0]));
        zone.addEventListener('dragover', e => { e.preventDefault(); zone.classList.add('over'); });
        zone.addEventListener('dragleave', () => zone.classList.remove('over'));
        zone.addEventListener('drop', e => {
            e.preventDefault();
            zone.classList.remove('over');
            handle(e.dataTransfer.files[0]);
        });
        zone.parentElement.querySelector('[data-cover-remove]')?.addEventListener('click', () => {
            hidden.value = '';
            img.classList.add('hidden');
        });
    });
})();
