function collapseSection(element) {
    // get the height of the element's inner content, regardless of its actual size

    var sectionWidth = element.scrollWidth;

    // temporarily disable all css transitions
    var elementTransition = element.style.transition;
    element.style.transition = '';

    // on the next frame (as soon as the previous style change has taken effect),
    // explicitly set the element's height to its current pixel height, so we
    // aren't transitioning out of 'auto'
    requestAnimationFrame(function() {
        element.style.width = sectionWidth + 'px';
        element.style.transition = elementTransition;

        // on the next frame (as soon as the previous style change has taken effect),
        // have the element transition to height: 0
        requestAnimationFrame(function() {
            element.style.width = 0 + 'px';
        });
    });

    // mark the section as "currently collapsed"
    element.setAttribute('data-collapsed', true);
}

function expandSection(element) {
    // get the height of the element's inner content, regardless of its actual size
    var sectionWidth = element.scrollWidth;

    // have the element transition to the height of its inner content
    element.style.width = sectionWidth + 'px';

    // when the next css transition finishes (which should be the one we just triggered)
    element.addEventListener('transitionend', function(e) {
        // remove this event listener so it only gets triggered once
        element.removeEventListener('transitionend', arguments.callee);

        // remove "height" from the element's inline styles, so it can return to its initial value
        element.style.width = null;
    });
}

function version2() {
    // This is the important part!

    var toggleButton = document.querySelector('#form-extension-switch');

    toggleButton.addEventListener('click', function() {

        const section = document.querySelector('.section.collapsible');
        var isCollapsed = section.getAttribute('data-collapsed') === 'true';

        if(isCollapsed) {
            expandSection(section)
            section.setAttribute('data-collapsed', false)
        } else {
            collapseSection(section)
        }
    });
}

function main() {
    version2();
}


window.addEventListener('onload', main());


