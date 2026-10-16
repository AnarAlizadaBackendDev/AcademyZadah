// Site-wide behaviour: theme switch, language switch, mobile nav, reveal animations, small helpers.
document.addEventListener('DOMContentLoaded', () => {
    const root = document.documentElement;
    const header = document.querySelector('.site-header');

    // Light / dark theme switch (remembered per browser)
    document.querySelector('[data-theme-toggle]')?.addEventListener('click', () => {
        const isDark = root.dataset.theme
            ? root.dataset.theme === 'dark'
            : matchMedia('(prefers-color-scheme: dark)').matches;
        const next = isDark ? 'light' : 'dark';
        root.classList.add('theme-anim');
        root.dataset.theme = next;
        try { localStorage.setItem('theme', next); } catch (e) {}
        setTimeout(() => root.classList.remove('theme-anim'), 500);
    });

    // Language switch: slide the indicator first, then navigate
    document.querySelectorAll('[data-seg] a').forEach(link => {
        link.addEventListener('click', e => {
            if (link.classList.contains('active')) { e.preventDefault(); return; }
            e.preventDefault();
            link.parentElement.style.setProperty('--i', link.dataset.index);
            setTimeout(() => { location.href = link.href; }, 260);
        });
    });

    // Mobile navigation
    document.querySelector('[data-nav-toggle]')?.addEventListener('click', () => header.classList.toggle('nav-open'));
    document.querySelectorAll('[data-nav] a').forEach(a => a.addEventListener('click', () => header.classList.remove('nav-open')));

    // Reveal on scroll
    const io = new IntersectionObserver(entries => entries.forEach(entry => {
        if (entry.isIntersecting) { entry.target.classList.add('in'); io.unobserve(entry.target); }
    }), {threshold: 0.12, rootMargin: '0px 0px -40px 0px'});
    document.querySelectorAll('.reveal, .reveal-group').forEach(el => io.observe(el));

    document.querySelectorAll('form[data-confirm]').forEach(form => {
        form.addEventListener('submit', e => { if (!confirm(form.dataset.confirm)) e.preventDefault(); });
    });

    document.querySelectorAll('[data-copy]').forEach(btn => {
        btn.addEventListener('click', async () => {
            await navigator.clipboard.writeText(document.querySelector(btn.dataset.copy).textContent);
            btn.textContent = '✓';
        });
    });

    document.querySelectorAll('[data-file-drop]').forEach(zone => {
        const input = zone.querySelector('input[type=file]');
        const label = zone.querySelector('span');
        input.addEventListener('change', () => { label.textContent = Array.from(input.files).map(f => f.name).join(', '); });
        ['dragenter', 'dragover'].forEach(ev => zone.addEventListener(ev, () => zone.classList.add('over')));
        ['dragleave', 'drop'].forEach(ev => zone.addEventListener(ev, () => zone.classList.remove('over')));
    });

    if (window.hljs) {
        document.querySelectorAll('.prose pre code, .code-window code').forEach(el => hljs.highlightElement(el));
    }
});
